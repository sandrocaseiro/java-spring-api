package dev.sandrocaseiro.template.models.io;

import dev.sandrocaseiro.template.utils.JsonUtil;
import lombok.Data;

import java.util.List;

@Data
public class IArquivoExtratoLote {
	private IArquivoExtratoLoteHeader headerLote;

	private List<IArquivoExtratoLoteDetalhe> detalhes;

	private IArquivoExtratoLoteTrailer trailerLote;

	@Override
	public String toString() {
		return JsonUtil.serializePrettyPrint(this);
	}
}
