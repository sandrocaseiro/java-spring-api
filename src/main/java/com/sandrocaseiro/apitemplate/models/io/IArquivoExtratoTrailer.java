package com.sandrocaseiro.apitemplate.models.io;

import com.sandrocaseiro.apitemplate.utils.JsonUtil;
import lombok.Data;

@Data
public class IArquivoExtratoTrailer {
	private int codBanco;

	private int lote;

	private int tipoRegistro;

	private String cnab;

	private int qtdLotes;

	private int qtdRegistros;

	private int qtdContas;

	private String cnab2;

	@Override
	public String toString() {
		return JsonUtil.serializePrettyPrint(this);
	}
}
