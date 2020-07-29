package dev.sandrocaseiro.template.models.io;

import dev.sandrocaseiro.template.utils.JsonUtil;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class IArquivoExtratoHeader {
	private int codBanco;

	private int lote;

	private int tipoRegistro;

	private String cnab;

	private int tipoInscricao;

	private long nroInscricao;

	private String codConvenio;

	private int nroAgencia;

	private String digitoAgencia;

	private long nroConta;

	private String digitoConta;

	private String digitoAgenciaConta;

	private String nomeEmpresa;

	private String nomeBanco;

	private String cnab2;

	private int codRemessaRetorno;

	private LocalDate dataGeracao;

	private LocalTime horaGeracao;

	private int sequenciaNSA;

	private int nroVersaoLayout;

	private int densidade;

	private String reservadoBanco;

	private String reservadoEmpresa;

	private String cnab3;

	@Override
	public String toString() {
		return JsonUtil.serializePrettyPrint(this);
	}
}
