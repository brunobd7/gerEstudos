package bruno.com.br.gerestudos;

public class Disciplina {

    private String id;
    private String nome;
    private String professor;

    public Disciplina(){}

    public Disciplina(String id, String nome, String professor){

        this.id = id;
        this.nome = nome;
        this.professor = professor;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
