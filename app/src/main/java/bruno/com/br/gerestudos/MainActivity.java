package bruno.com.br.gerestudos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtNome;
    Button btnAddProfessor;
    Spinner spinnerDisciplinas;

    // referência para o banco de dados no firebase.
    // Traduzido da documentação:
    // "uma referência para o firebase representa um local particular
    // no seu banco de dados e pode ser usado para leitura e escrita
    // de dados naquele local.
    // Esta classe é o ponto de partida para todas as operações
    // de banco de dados."
    DatabaseReference databaseDisciplinas;
    ListView listViewDisciplinas;
    List<Disciplina> disciplinaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseDisciplinas = FirebaseDatabase.getInstance().getReference("Disciplinas");

		//Capturando os dados inseridos na view
        txtNome = findViewById(R.id.txtNome);
        btnAddProfessor = findViewById(R.id.btnAddProfessor);
        spinnerDisciplinas = findViewById(R.id.spinnerDisciplinas);

        listViewDisciplinas = (ListView) findViewById(R.id.listViewDisciplinas);
        disciplinaList = new ArrayList<>();
		
		//USANDO O EVENTO PARA ACIONAR O METODO 
        btnAddProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //CHAMANDO O METODO DE ADICIONAR O PROFESSOR DEFINIDO LOGO ABAIXO
                addProfessor();
            }
        });

    }

    @Override
    protected void onStart() {

        super.onStart();

        // criando um tratador de eventos relacionado com nosso
        // banco de dados Firebase
        databaseDisciplinas.addValueEventListener( new ValueEventListener() {

            // método chamado automaticamente quando houver mudança nos dados
            // armazenados no firebase

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                disciplinaList.clear();

                // Recebemos um objeto DataSnapshot, que tem os dados
                // apontados por nossa referencia no firebase, isto é,
                // os dados que estão "debaixo" da chave "Disciplina".
                // Vamos então "varrer" esse objeto, pegando os dados lá dentro
                // e criando objetos da nossa classe Disciplina, para colocar na lista
                for(DataSnapshot disciplinaSnapshot : dataSnapshot.getChildren()) {

                    // artistSnapshot tem um dos "filhos" de "Artists", isto é,
                    // tem os dados de um artista.
                    // Vamos então criar um objeto artista, a partir desses dados
                    //
                    // ... getValue(Artist.class)
                    //     pegue os dados, e a partir deles crie um objeto da
                    //     classe Artist.
                    Disciplina disciplina = disciplinaSnapshot.getValue(Disciplina.class);

                    // enfim, colocamos o objeto artista criado a partir dos dados lidos
                    // na nossa lista de artistas
                    disciplinaList.add(disciplina);
                }

                // agora que temos nossa lista de artistas atualizada,
                // podemos criar o adapter que vai ser responsável por
                // colocar esses dados no ListView,
                // passando nossa lista para este adapter
               DisciplinaListAdapter adapter = new DisciplinaListAdapter(MainActivity.this, disciplinaList);

                // finalmente, informamos ao ListView quem é o adapter que vai
                // exibir os dados
                listViewDisciplinas.setAdapter(adapter);
            }

            // método chamado no caso de algum erro no banco de dados
            // (ainda dentro do listener).
            // Não definiremos nada neste momento.
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    public void addProfessor(){
        // trim() - retira os espaços em branco antes e depois
        String nome = txtNome.getText().toString().trim();

        String disciplinas = spinnerDisciplinas.getSelectedItem().toString();
        if( !TextUtils.isEmpty(nome) ) {

            String id = databaseDisciplinas.push().getKey();
            Disciplina disciplina = new Disciplina(id,nome,disciplinas);

            databaseDisciplinas.child(id).setValue(disciplina);

            // toast avisando que o artista foi adicionado
            Toast.makeText(this, "Disciplina adicionada", Toast.LENGTH_LONG).show();
        }
        else {
            // se estiver vazia, avisa o usuário com um "toast"
            Toast.makeText(this, "Você deve digitar um nome", Toast.LENGTH_LONG).show();
        }
    }
}
