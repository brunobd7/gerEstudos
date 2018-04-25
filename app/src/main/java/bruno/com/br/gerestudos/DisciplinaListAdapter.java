package bruno.com.br.gerestudos;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class DisciplinaListAdapter extends ArrayAdapter<Disciplina> {

    private Activity contexto;
    private List<Disciplina> disciplinaList;

    public DisciplinaListAdapter(Activity contexto, List<Disciplina> disciplinaList){

        super(contexto, R.layout.layout_professor_item, disciplinaList);
        this.contexto = contexto;
        this.disciplinaList = disciplinaList;


    }

    public View getView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = contexto.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_professor_item, null, true);

        TextView txtViewNome = (TextView) listViewItem.findViewById(R.id.txtViewNome);
        TextView txtViewDisciplina = (TextView) listViewItem.findViewById(R.id.txtViewDisciplina);

        Disciplina disciplina = disciplinaList.get(position);

        txtViewNome.setText(disciplina.getProfessor());
        txtViewDisciplina.setText(disciplina.getNome());

        return listViewItem;

    }


}
