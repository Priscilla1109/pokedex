package com.pokedex.pokedex.repository;

import com.pokedex.pokedex.model.EvolutionDetail;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EvolutionDetailRepository {
    private final JdbiEvolutionDetailRepository jdbiEvolutionDetailRepository;

    public EvolutionDetail save(final EvolutionDetail evolutionDetail) throws Throwable {
        Optional<EvolutionDetail> evolutionOpt = jdbiEvolutionDetailRepository.findByPokemonAndEvolution(
            evolutionDetail.getSelf().getNumber(),
            evolutionDetail.getEvolution().getNumber());

        evolutionOpt.ifPresentOrElse(
            existEvolution -> {
                update(evolutionDetail);
        },
            () -> jdbiEvolutionDetailRepository.save(evolutionDetail)
        );

        return jdbiEvolutionDetailRepository.findByPokemonAndEvolution(evolutionDetail.getSelf().getNumber(), evolutionDetail.getEvolution().getNumber())
            .orElseThrow(() -> new RuntimeException("Failed to save or update the EvolutionDetail"));
    }

    private void update(EvolutionDetail evolutionDetail) {
        jdbiEvolutionDetailRepository.update(evolutionDetail);
    }

    public List<EvolutionDetail> findAll(Pageable pageable) {
        return jdbiEvolutionDetailRepository.findAll(pageable);
    }

    public List<EvolutionDetail> findBySelfNumber(Long selfNumber) {
        return jdbiEvolutionDetailRepository.findBySelfNumber(selfNumber);
    }
}
