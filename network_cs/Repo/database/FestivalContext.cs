using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Repo.database.EFCoreModels;
using System.Configuration;

namespace Repo.database;

public partial class FestivalContext : DbContext
{
    private string ConString;
    public FestivalContext()
    {
    }

    public FestivalContext(string  connectionString)
    {
        ConString = connectionString;
    }

    public FestivalContext(DbContextOptions<FestivalContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Angajati> Angajatis { get; set; }

    public virtual DbSet<Artisti> Artistis { get; set; }

    public virtual DbSet<Concerte> Concertes { get; set; }

    public virtual DbSet<Locatii> Locatiis { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        => optionsBuilder.UseSqlite(ConString);

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Angajati>(entity =>
        {
            entity.ToTable("angajati");

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Parola).HasColumnName("parola");
            entity.Property(e => e.Username).HasColumnName("username");
        });

        modelBuilder.Entity<Artisti>(entity =>
        {
            entity.ToTable("artisti");

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Nume).HasColumnName("nume");
        });

        modelBuilder.Entity<Concerte>(entity =>
        {
            entity.ToTable("concerte");

            entity.Property(e => e.Id)
                .ValueGeneratedNever()
                .HasColumnName("id");
            entity.Property(e => e.Artist).HasColumnName("artist");
            entity.Property(e => e.Data)
                .HasColumnType("timestamp")
                .HasColumnName("data");
            entity.Property(e => e.Locatie).HasColumnName("locatie");
            entity.Property(e => e.LocuriDisponibile).HasColumnName("locuri_disponibile");
            entity.Property(e => e.LocuriOcupate).HasColumnName("locuri_ocupate");

            entity.HasOne(d => d.ArtistNavigation).WithMany(p => p.Concertes)
                .HasForeignKey(d => d.Artist)
                .OnDelete(DeleteBehavior.ClientSetNull);

            entity.HasOne(d => d.LocatieNavigation).WithMany(p => p.Concertes)
                .HasForeignKey(d => d.Locatie)
                .OnDelete(DeleteBehavior.ClientSetNull);
        });

        modelBuilder.Entity<Locatii>(entity =>
        {
            entity.ToTable("locatii");

            entity.Property(e => e.Id).HasColumnName("id");
            entity.Property(e => e.Numar).HasColumnName("numar");
            entity.Property(e => e.Oras).HasColumnName("oras");
            entity.Property(e => e.Strada).HasColumnName("strada");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
