package com.sandrocaseiro.apitemplate.models.io;

import com.sandrocaseiro.apitemplate.utils.JsonUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IArquivoExtratoLoteDetalhe {
	private int codBanco;

	private int lote;

	private int tipoRegistro;

	private int sequencia;

	private String segmento;

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

	private String cnab2;

	private String natureza;

	private int tipoComplemento;

	private String complemento;

	private String cpmf;

	private LocalDate dtaContabil;

	private LocalDate dtaLancamento;

	private BigDecimal vlrLancamento;

	private String tipoLancamento;

	private int categoriaLancamento;

	private String codHistorico;

	private String descricaoHistorico;

	private String nroDocumento;

	@Override
	public String toString() {
		return JsonUtil.serializePrettyPrint(this);
	}
}
