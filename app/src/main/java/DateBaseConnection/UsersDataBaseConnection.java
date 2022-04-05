package DateBaseConnection;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

import Intrfaces.LoginCaller;
import Intrfaces.PasswordReseter;
import Intrfaces.UserAdder;
import Intrfaces.UsersGetter;
import utils.User;

public class UsersDataBaseConnection {

    private static FirebaseAuth DataBaseAuth = FirebaseAuth.getInstance();
    private static FirebaseDatabase DataBase = FirebaseDatabase.getInstance();

    private UsersDataBaseConnection(){ }    // disable the default constructor

    /**
     * register a new user to the app
     * @param user - new user to register
     * @return true is the process was successful, and false is not
     */
    public static void AddUser(UserAdder calledFrom, User user){

        // add user to the data base
        DataBaseAuth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){    // if the upload was successful

                    // upload all the data of the user
                    DataBase.getReference("Users").child(DataBaseAuth.getCurrentUser().getUid()).
                            setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){    // if the upload was successful, send verification email
                                DataBaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){ // if the email did not send
                                            calledFrom.userAdded(true);
                                        }
                                        else {
                                            calledFrom.userAdded(false);
                                        }
                                    }
                                });
                            }
                            else{
                                calledFrom.userAdded(false);                            }
                        }
                    });
                }
                else{
                    calledFrom.userAdded(false);
                }
            }
        });
    }




    public static void login(LoginCaller calledFrom, String email, String password, boolean manager){

        DataBaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()){
                    if(DataBaseAuth.getCurrentUser().isEmailVerified()) {
                        DatabaseReference data_ref= DataBase.getReference("Users/"+ DataBaseAuth.getCurrentUser().getUid());
                        data_ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                User us = snapshot.getValue(User.class);
                                if (manager) {
                                    if (us.getIsManager()) {
                                        calledFrom.finishLogin(us, 1);
                                    } else {
                                        calledFrom.finishLogin(null, 4);
                                    }
                                }
                                else{
                                    calledFrom.finishLogin(us, 0);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) { }
                        });
                    }
                    else{
                        calledFrom.finishLogin(null, 3);
                    }
                }
                else{
                    calledFrom.finishLogin(null, 2);
                }
            }
        });
    }

    /**
     * send a email to reset the password
     * @param calledFrom
     */
    public static void passwordReset(PasswordReseter calledFrom, String email){
        DataBaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                calledFrom.onResetResults(task.isSuccessful());
            }
        });
    }


    /**
     * get all registered users
     * @param calledFrom
     */
    public static void getAllUsers(UsersGetter calledFrom){

        List<User> usersList = new LinkedList<>();

        DataBase.getReference("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    usersList.add(user);
                }
                calledFrom.gotUsers(usersList);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }


}
