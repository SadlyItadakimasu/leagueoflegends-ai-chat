package me.kuma.leagueoflegendsaichat.adapters.out;

import me.kuma.leagueoflegendsaichat.domain.model.Champion;
import me.kuma.leagueoflegendsaichat.domain.ports.ChampionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public class ChampionJdbcRepository implements ChampionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Champion> championsRowMapper;

    public ChampionJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.championsRowMapper = (rs, rowNum) -> new Champion(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("role"),
                rs.getString("lore"),
                rs.getString("image_url")
        );
    }

    @Override
    public List<Champion> findAll() {
        return jdbcTemplate.query("SELECT * FROM CHAMPION", championsRowMapper);
    }

    @Override
    public Optional<Champion> findById(Long id) {
        String sql = "SELECT * FROM CHAMPION WHERE ID = ?";
        List<Champion> champion = jdbcTemplate.query(sql, championsRowMapper, id);
        return champion.stream().findFirst();
    }
}
