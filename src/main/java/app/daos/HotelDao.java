package app.daos;

import app.dtos.PoemDto;
import app.entities.Poem;
import app.exceptions.DaoException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import app.entities.Hotel;
import app.dtos.HotelDto;
import jakarta.persistence.TypedQuery;

import java.util.LinkedList;
import java.util.List;

public class HotelDao {

    private static HotelDao instance;
    private static EntityManagerFactory emf;

    private HotelDao(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public static HotelDao getInstance(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new HotelDao(emf);
        }
        return instance;
    }

    public static HotelDao getInstance() {
        return instance;
    }

    public Hotel create(Hotel hotel) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();
            return hotel;
        }
    }

    public HotelDto create(HotelDto hotelDto) throws DaoException {
        try (EntityManager em = emf.createEntityManager()) {
            Hotel hotel = new Hotel(hotelDto);
            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();
            return new HotelDto(hotel);
        } catch (RuntimeException e) {
            throw new DaoException("Error creating hotel");
        }
    }

    public List<HotelDto> readAll() throws DaoException {
        try (EntityManager em = emf.createEntityManager()) {

            String jpql = "SELECT h FROM Hotel h ORDER BY id";
            TypedQuery<Hotel> query = em.createQuery(jpql, Hotel.class);
            List<Hotel> hotels = query.getResultList();

            List<HotelDto> hotelDtos = new LinkedList<>();
            for (Hotel h : hotels) {
                hotelDtos.add(new HotelDto(h));
            }
            return hotelDtos;

        } catch (RuntimeException e) {
            throw new DaoException("Error reading all");
        }
    }

}