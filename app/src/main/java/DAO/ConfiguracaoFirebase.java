package DAO;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Cacai on 29/10/2017.
 */

public class ConfiguracaoFirebase {


    private static DatabaseReference referenciaFireBase;
    private static FirebaseAuth autenticacao;

    public static DatabaseReference getFirebase(){
        if (referenciaFireBase == null){
            referenciaFireBase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFireBase;
    }

    public static FirebaseAuth getFirebaseAutenticacao(){
        if (autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }


}
