package mestrado.git;

public class CommitManager {
	private int commitInicial;
	private int commitFinal;
	private int posicaoProjeto;
	
	
	public int getCommitInicial() {
		return commitInicial;
	}
	public CommitManager(int commitInicial, int commitFinal, int posicaoProjeto) {
		super();
		this.commitInicial = commitInicial;
		this.commitFinal = commitFinal;
		this.posicaoProjeto = posicaoProjeto;
	}
	
	public void setCommitInicial(int commitInicial) {
		this.commitInicial = commitInicial;
	}
	
	public int getCommitFinal() {
		return commitFinal;
	}
	public void setCommitFinal(int commitFinal) {
		this.commitFinal = commitFinal;
	}
	
	public int getPosicaoProjeto() {
		return posicaoProjeto;
	}
	
	public void setPosicaoProjeto(int posicaoProjeto) {
		this.posicaoProjeto = posicaoProjeto;
	}	
}