package darkchoco.taco.data;

import darkchoco.taco.Ingredient;
import darkchoco.taco.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    private void saveIngredientToTaco(Ingredient ingredient, long tacoId) {
        jdbcTemplate.update(
                "INSERT INTO taco_ingredients (taco, ingredient) VALUES (?, ?)",
                tacoId, ingredient.getId()
        );
    }

    private long saveTacoInfo(Taco taco) {
        taco.setCreated(new Date());

        PreparedStatementCreatorFactory preparedStatementCreatorFactory =
                new PreparedStatementCreatorFactory(
                        "INSERT INTO taco (name, created) VALUES (?, ?)",
                        Types.VARCHAR, Types.DATE
                );
        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);

        PreparedStatementCreator psc =
                preparedStatementCreatorFactory.newPreparedStatementCreator(
                        Arrays.asList(
                                taco.getName(),
                                new Date(taco.getCreated().getTime())
                        )
                );

//        PreparedStatementCreator psc =
//                new PreparedStatementCreatorFactory(
//                        "INSERT INTO taco (name, created) VALUES (?, ?)",
//                        Types.VARCHAR, Types.DATE
//                ).newPreparedStatementCreator(
//                        Arrays.asList(
//                                taco.getName(),
//                                new Date(taco.getCreated().getTime())
//                        )
//                );
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        return keyHolder.getKey().longValue();
    }
}
