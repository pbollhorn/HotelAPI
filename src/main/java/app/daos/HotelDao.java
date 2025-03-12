package app.daos;

import java.util.List;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import app.entities.Hotel;
import app.dtos.HotelDto;
import app.exceptions.DaoException;
import app.exceptions.IdNotFoundException;

public class HotelDao
{

    private static HotelDao instance;
    private static EntityManagerFactory emf;

    private HotelDao(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public static HotelDao getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new HotelDao(emf);
        }
        return instance;
    }

    public static HotelDao getInstance()
    {
        return instance;
    }


    public List<HotelDto> getAll() throws DaoException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            String jpql = "SELECT h FROM Hotel h ORDER BY id";
            TypedQuery<Hotel> query = em.createQuery(jpql, Hotel.class);
            List<Hotel> hotels = query.getResultList();
            List<HotelDto> hotelDtos = hotels.stream().map(HotelDto::new).toList();
            return hotelDtos;
        }
        catch (RuntimeException e)
        {
            throw new DaoException("Error reading all");
        }

    }


    public HotelDto get(int id) throws DaoException, IdNotFoundException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Hotel hotel = em.find(Hotel.class, id);
            if (hotel == null)
            {
                throw new IdNotFoundException("No hotel with id=" + id);
            }
            return new HotelDto(hotel);
        }
        catch (RuntimeException e)
        {
            throw new DaoException("error in get");
        }
    }

    // This method takes a Hotel entity as input, and is only used to populate database in the beginning
    public HotelDto createFromEntity(Hotel hotel) throws DaoException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();
            return new HotelDto(hotel);
        }
        catch (RuntimeException e)
        {
            throw new DaoException("Error in create");
        }
    }


    public HotelDto create(HotelDto hotelDto) throws DaoException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Hotel hotel = new Hotel(hotelDto);

            em.getTransaction().begin();
            em.persist(hotel);
            em.getTransaction().commit();

            return new HotelDto(hotel);
        }
        catch (RuntimeException e)
        {
            throw new DaoException("Error creating hotel");
        }
    }

    public HotelDto update(int id, HotelDto hotelDto) throws DaoException, IdNotFoundException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Hotel hotel = em.find(Hotel.class, id);
            if (hotel == null)
            {
                throw new IdNotFoundException("No hotel with id=" + id);
            }

            hotel = new Hotel(id, hotelDto);

            em.getTransaction().begin();
            hotel = em.merge(hotel);
            em.getTransaction().commit();

            return new HotelDto(hotel);
        }
        catch (RuntimeException e)
        {
            throw new DaoException("Error creating hotel");
        }
    }

    public HotelDto delete(int id) throws DaoException, IdNotFoundException
    {
        try (EntityManager em = emf.createEntityManager())
        {
            Hotel hotel = em.find(Hotel.class, id);
            if (hotel == null)
            {
                throw new IdNotFoundException("No hotel with id=" + id);
            }
            em.getTransaction().begin();
            em.remove(hotel);
            em.getTransaction().commit();

            return new HotelDto(hotel);
        }
        catch (RuntimeException e)
        {
            throw new DaoException("error in delete");
        }
    }


}