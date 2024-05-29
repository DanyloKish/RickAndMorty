package mate.academy.rickandmorty.repository;

import java.util.List;
import java.util.Set;
import mate.academy.rickandmorty.model.CartoonCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<CartoonCharacter, Long> {
    List<CartoonCharacter> findAllByExternalIdIn(Set<Long> externalIds);

    List<CartoonCharacter> findAllByNameContains(String namePart);
}
