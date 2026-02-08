package demoanalyzer.com.dem.core.persistence.mapper;

import demoanalyzer.com.dem.core.domain.model.Dem;
import demoanalyzer.com.dem.core.domain.model.header.Header;
import demoanalyzer.com.dem.core.domain.model.metadata.Metadata;

import demoanalyzer.com.dem.core.persistence.entity.DemEntity;
import demoanalyzer.com.dem.core.persistence.entity.header.HeaderEntity;
import demoanalyzer.com.dem.core.persistence.entity.metadata.MetadataEntity;
import demoanalyzer.com.user.auth.persistence.AuthUserEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DemPersistenceMapper {

  private final EntityManager entityManager;

  public DemEntity toEntity(Dem dem) {
    if (dem == null) return null;

    return DemEntity.builder()
        .id(dem.getId())
        .metadata(mapMetadataToEntity(dem.getMetadata()))
        .header(mapHeaderToEntity(dem.getHeader()))

        // --- Zespoły ---
        .teamA(dem.getTeamA())
        .teamB(dem.getTeamB())

        // --- Nowe statystyki analityczne (JSONB) ---
        .entryFrags(dem.getEntryFrags())
        .clutches(dem.getClutches())
        .tradeKills(dem.getTradeKills())
        .sideWins(dem.getSideWins())

        // --- Statystyki szczegółowe (JSONB) ---
        .statsAdr(dem.getStatsAdr())
        .statsKast(dem.getStatsKast())
        .statsRating(dem.getStatsRating())
        .build();
  }

  public Dem toDomain(DemEntity entity) {
    if (entity == null) return null;

    return Dem.builder()
        .id(entity.getId())
        .metadata(mapMetadataToDomain(entity.getMetadata()))
        .header(mapHeaderToDomain(entity.getHeader()))

        // --- Zespoły ---
        .teamA(entity.getTeamA())
        .teamB(entity.getTeamB())

        // --- Nowe statystyki analityczne ---
        .entryFrags(entity.getEntryFrags())
        .clutches(entity.getClutches())
        .tradeKills(entity.getTradeKills())
        .sideWins(entity.getSideWins())

        // --- Statystyki szczegółowe ---
        .statsAdr(entity.getStatsAdr())
        .statsKast(entity.getStatsKast())
        .statsRating(entity.getStatsRating())
        .build();
  }

  // --- Metody pomocnicze (bez zmian) ---

  private MetadataEntity mapMetadataToEntity(Metadata metadata) {
    if (metadata == null) return null;

    /*
     * Retrieves a proxy reference to the AuthUserEntity to establish the relationship without
     * triggering an unnecessary database fetch.
     */
    AuthUserEntity ownerRef =
        entityManager.getReference(AuthUserEntity.class, metadata.getOwnerId());

    return new MetadataEntity(
        ownerRef, metadata.getCreatedAt(), metadata.getFinishedAt(), metadata.getStatus());
  }

  private Metadata mapMetadataToDomain(MetadataEntity entity) {
    if (entity == null) return null;
    return new Metadata(
        entity.getOwner().getId(),
        entity.getCreatedAt(),
        entity.getFinishedAt(),
        entity.getStatus());
  }

  private HeaderEntity mapHeaderToEntity(Header header) {
    if (header == null) return null;
    return new HeaderEntity(header.mapName(), header.serverName());
  }

  private Header mapHeaderToDomain(HeaderEntity entity) {
    if (entity == null) return null;
    return new Header(entity.getMapName(), entity.getServerName());
  }
}
