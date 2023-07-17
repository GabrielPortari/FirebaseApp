package com.example.firebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private Button botaoSalvar;
    private ImageView imagem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botaoSalvar = findViewById(R.id.button);
        imagem = findViewById(R.id.imageView3);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagem.setDrawingCacheEnabled(true);
                imagem.buildDrawingCache();

                //transforma a imagem em bitmap
                Bitmap bitmap = imagem.getDrawingCache();
                //transforma o bitmap em jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                byte[] dadosImg = baos.toByteArray();

                //Definir nós para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                StorageReference imagens = storageReference.child("imagens");

                /* BAIXAR AQUIVOS
                StorageReference imgRef = imagens.child("12c8b833-fed1-4fbc-8f1a-51e8b7da2226.jpeg.jpeg"); // nome do arquivo
                Glide.with(MainActivity.this).using(new FirebaseImageLoader()).load(imgRef).into(imagem); // baixa a imagem do firebase e coloca no imageview da main
                */

                /* EXCLUIR ARQUIVOS
                StorageReference imgRef = imagens.child("img.jpeg"); // nome do arquivo

                imgRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Erro ao excluir" + e.getMessage().toString(), Toast.LENGTH_SHORT ).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(MainActivity.this, "Sucesso ao excluir", Toast.LENGTH_SHORT ).show();
                    }
                });*/

                //SALVAR ARQUIVO NO FIREBASE
                /*
                // Nome do arquivo
                String nomeArq = UUID.randomUUID().toString();
                StorageReference imgRef = imagens.child(nomeArq + ".jpeg");

                //Object que ira controlar o upload
                UploadTask uploadTask = imgRef.putBytes(dadosImg);

                uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Upload Falhou: " + e.getMessage().toString(), Toast.LENGTH_SHORT ).show();
                    }
                }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imgRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                Uri url = task.getResult();
                                Toast.makeText(MainActivity.this, "Upload Sucesso: ", Toast.LENGTH_SHORT ).show();
                            }
                        });
                    }
                });*/
            }
        });

        //---------------------------------------------------
        //Deslogar Usuario
        //auth.signOut();
        /*
        //Logar usuario
        auth.signInWithEmailAndPassword("pedrinho@gmail.com", "pedrinhogoku123")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("CreateUser", "Sucesso ao fazer login");
                        }else{
                            Log.i("CreateUser", "Erro ao fazer login");
                        }
                    }
                });
        */
        /*
        //Verificar usuario logado
        if(auth.getCurrentUser() != null){
            Log.i("CreateUser", "Usuario Logado");
        }else{
            Log.i("CreateUser", "Usuario Deslogado");
        }
        //Criação de autenticação
        auth.createUserWithEmailAndPassword("pedrinho@gmail.com","pedrinhogoku123")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("CreateUser", "Sucesso ao cadastrar usuario");
                        }else{
                            Log.i("CreateUser", "Erro ao cadastrar usuario");
                        }
                    }
                });

         */

        // CRIA-SE A REFERENCIA A USUARIOS
        /*DatabaseReference users = reference.child("user");
        SALVAR DADOS
        //CRIA-SE OS USUARIOS
        User user = new User("Pedrinho", "Fimose", 18);
        User user2 = new User("Juninho", "Picamole", 24);
        User user3 = new User("Flaviano", "Monobola", 35);
        //SALVA O USUARIOS NO FIREBASE
        users.push().setValue(user);
        users.push().setValue(user2);
        users.push().setValue(user3);

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FIREBASE", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        */

        //BUSCAR USUARIO
        /*
        //DatabaseReference userSearch = users.child("-N_ZvqTXt576cYUOakBn");
        //Query userSearch = users.orderByChild("nome").equalTo("Pedrinho");
        //Query userSearch = users.orderByKey().limitToFirst(2);
        Query userSearch = users.orderByChild("idade").startAt(20);
        // .startAt para valores < 20
        // .endAt para valores > 20
        // entre dois valores utilizar .startAt(10).endAt(30) == tambem funciona com letras, basta usar .endAt("A" + "\uf8ff")
        userSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //User dadosUser = snapshot.getValue(User.class);
                //Log.i("Dados User", dadosUser.getNome());
                Log.i("Dados User", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         */



    }
}