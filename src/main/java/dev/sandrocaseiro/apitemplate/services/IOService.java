package dev.sandrocaseiro.apitemplate.services;

import dev.sandrocaseiro.apitemplate.models.io.IArquivoExtrato;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.beanio.BeanReader;
import org.beanio.BeanReaderException;
import org.beanio.BeanWriter;
import org.beanio.InvalidRecordException;
import org.beanio.RecordContext;
import org.beanio.StreamFactory;
import org.beanio.UnexpectedRecordException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Map;

@Service
@Log4j2
public class IOService {
    public IArquivoExtrato readFile() {
        StreamFactory factory = StreamFactory.newInstance();
        factory.load("src/main/resources/io/layout-file.xml");

        BeanReader br = factory.createReader("extrato-arquivo", new File("src/main/resources/io/data"));
        IArquivoExtrato extrato = null;

        try {
            while ((extrato = (IArquivoExtrato) br.read()) != null) {
                log.info("{}", extrato);
                return extrato;
            }
        }
        catch (InvalidRecordException | UnexpectedRecordException e) {
            treatException(e);
        }
        finally {
            br.close();
        }

        return extrato;
    }

    public void generateFile(IArquivoExtrato extrato) throws IOException {
        StreamFactory factory = StreamFactory.newInstance();
        factory.load("src/main/resources/io/layout-file.xml");
        String destPath = "src/main/resources/io/new-data";

        Files.delete(Paths.get(destPath));

        BeanWriter bw = factory.createWriter("extrato-arquivo", new File(destPath));
        bw.write(extrato);
        bw.flush();
        bw.close();
    }

    private static void treatException(BeanReaderException e) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < e.getRecordCount(); i++) {
            RecordContext context = e.getRecordContext(i);
            if (!context.hasErrors())
                continue;

            sb.append(String.format("Record %s errors", context.getRecordName()));
            sb.append("\r\n");

            if (context.hasRecordErrors()) {
                sb.append(String.format("    >> Record errors: %s", String.join(", ", context.getRecordErrors())));
                sb.append("\r\n");
            }

            if (context.hasFieldErrors()) {
                for (Map.Entry<String, Collection<String>> field : context.getFieldErrors().entrySet()) {
                    sb.append(String.format("    >> Field %s errors: %s", field.getKey(), String.join(", ", field.getValue())));
                    sb.append("\r\n");
                }
            }
        }

        log.error("{}", sb);
    }
}
