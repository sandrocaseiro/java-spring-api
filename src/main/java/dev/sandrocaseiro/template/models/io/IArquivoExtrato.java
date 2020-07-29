package dev.sandrocaseiro.template.models.io;

import dev.sandrocaseiro.template.utils.JsonUtil;
import lombok.Data;

import java.util.List;

@Data
public class IArquivoExtrato {
	private IArquivoExtratoHeader header;

	private List<IArquivoExtratoLote> lotes;

	private IArquivoExtratoTrailer trailer;

	@Override
	public String toString() {
		return JsonUtil.serializePrettyPrint(this);
	}
}
