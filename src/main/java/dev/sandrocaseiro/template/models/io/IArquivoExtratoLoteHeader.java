package dev.sandrocaseiro.template.models.io;

import dev.sandrocaseiro.template.models.io.enums.IPosicaoSaldo;
import dev.sandrocaseiro.template.models.io.enums.ISituacaoSaldo;
import dev.sandrocaseiro.template.utils.JsonUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IArquivoExtratoLoteHeader {
	private int codBanco;

	private int lote;

	private int tipoRegistro;

	private String tipoOperacao;

	private int tipoServico;

	private int formaLancamento;

	private int layoutLote;

	private String cnab;

	private int tipoInscricao;

	private long nroInscricao;

	private String codConvenio;

	private int nroAgencia;

	private String digitoAgencia;

	private String nroConta;

	private String digitoConta;

	private String digitoAgenciaConta;

	private String nomeEmpresa;

	private String cnab2;

	private LocalDate dtaSaldo;

	private BigDecimal vlrSaldo;

	private ISituacaoSaldo situacaoSaldo;

	private IPosicaoSaldo posicaoSaldo;

	private String moeda;

	private int sequencia;

	private String cnab3;

	@Override
	public String toString() {
		return JsonUtil.serializePrettyPrint(this);
	}
}
