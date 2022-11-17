package aulas.web.adivinhe.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.json.bind.adapter.JsonbAdapter;

/**
 * Adaptador para conversão de datas.
 * Este adaptador converte um objeto {@code java.util.Date} para {@code String}\
 * e vice-versa.
 * Não deveria ser necessário criar este adaptador porém existe um problema com
 * a implementação da anotação {@code JsonbDateFormat} que somente deserializa
 * corretamente o tipo {@code java.time.LocalDate}.
 * Isto pode ser considerado um bug já que a especificação claramente estabelece
 * que o tipo {@code java.util.Date} deve ser tratado corretamente independentemente
 * de qualquer anotação.
 * @author Wilson Horstmeyer Bogado
 */
public class DateAdapter implements JsonbAdapter<Date, String> {
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String adaptToJson(Date orgnl) throws Exception {
        return DATE_FORMAT.format(orgnl);
    }

    @Override
    public Date adaptFromJson(String adptd) throws Exception {
        return DATE_FORMAT.parse(adptd);
    }
}
