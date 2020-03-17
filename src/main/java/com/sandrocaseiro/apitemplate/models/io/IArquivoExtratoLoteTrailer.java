package com.sandrocaseiro.apitemplate.models.io;

import com.sandrocaseiro.apitemplate.models.io.enums.IPosicaoSaldo;
import com.sandrocaseiro.apitemplate.models.io.enums.ISituacaoSaldo;
import com.sandrocaseiro.apitemplate.utils.JsonUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class IArquivoExtratoLoteTrailer {
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

	private String cnab2;

	private BigDecimal vlrBloqueadoAcima24h;

	private BigDecimal vlrLimite;

	private BigDecimal vlrBloqueado24h;

	private LocalDate dtaSaldo;

	private BigDecimal vlrSaldo;

	private ISituacaoSaldo situacaoSaldo;

	private IPosicaoSaldo posicaoSaldo;

	private int qtdRegistros;

	private BigDecimal vlrDebitos;

	private BigDecimal vlrCreditos;

	private String cnab3;

	@Override
	public String toString() {
		return JsonUtil.serializePrettyPrint(this);
	}
}
