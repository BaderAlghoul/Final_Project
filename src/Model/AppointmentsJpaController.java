/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Model.exceptions.IllegalOrphanException;
import Model.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Bader M
 */
public class AppointmentsJpaController implements Serializable {

    public AppointmentsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Appointments appointments) {
        if (appointments.getBookedAppointmentsCollection() == null) {
            appointments.setBookedAppointmentsCollection(new ArrayList<BookedAppointments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<BookedAppointments> attachedBookedAppointmentsCollection = new ArrayList<BookedAppointments>();
            for (BookedAppointments bookedAppointmentsCollectionBookedAppointmentsToAttach : appointments.getBookedAppointmentsCollection()) {
                bookedAppointmentsCollectionBookedAppointmentsToAttach = em.getReference(bookedAppointmentsCollectionBookedAppointmentsToAttach.getClass(), bookedAppointmentsCollectionBookedAppointmentsToAttach.getId());
                attachedBookedAppointmentsCollection.add(bookedAppointmentsCollectionBookedAppointmentsToAttach);
            }
            appointments.setBookedAppointmentsCollection(attachedBookedAppointmentsCollection);
            em.persist(appointments);
            for (BookedAppointments bookedAppointmentsCollectionBookedAppointments : appointments.getBookedAppointmentsCollection()) {
                Appointments oldAppointmentIdOfBookedAppointmentsCollectionBookedAppointments = bookedAppointmentsCollectionBookedAppointments.getAppointmentId();
                bookedAppointmentsCollectionBookedAppointments.setAppointmentId(appointments);
                bookedAppointmentsCollectionBookedAppointments = em.merge(bookedAppointmentsCollectionBookedAppointments);
                if (oldAppointmentIdOfBookedAppointmentsCollectionBookedAppointments != null) {
                    oldAppointmentIdOfBookedAppointmentsCollectionBookedAppointments.getBookedAppointmentsCollection().remove(bookedAppointmentsCollectionBookedAppointments);
                    oldAppointmentIdOfBookedAppointmentsCollectionBookedAppointments = em.merge(oldAppointmentIdOfBookedAppointmentsCollectionBookedAppointments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Appointments appointments) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Appointments persistentAppointments = em.find(Appointments.class, appointments.getId());
            Collection<BookedAppointments> bookedAppointmentsCollectionOld = persistentAppointments.getBookedAppointmentsCollection();
            Collection<BookedAppointments> bookedAppointmentsCollectionNew = appointments.getBookedAppointmentsCollection();
            List<String> illegalOrphanMessages = null;
            for (BookedAppointments bookedAppointmentsCollectionOldBookedAppointments : bookedAppointmentsCollectionOld) {
                if (!bookedAppointmentsCollectionNew.contains(bookedAppointmentsCollectionOldBookedAppointments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BookedAppointments " + bookedAppointmentsCollectionOldBookedAppointments + " since its appointmentId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<BookedAppointments> attachedBookedAppointmentsCollectionNew = new ArrayList<BookedAppointments>();
            for (BookedAppointments bookedAppointmentsCollectionNewBookedAppointmentsToAttach : bookedAppointmentsCollectionNew) {
                bookedAppointmentsCollectionNewBookedAppointmentsToAttach = em.getReference(bookedAppointmentsCollectionNewBookedAppointmentsToAttach.getClass(), bookedAppointmentsCollectionNewBookedAppointmentsToAttach.getId());
                attachedBookedAppointmentsCollectionNew.add(bookedAppointmentsCollectionNewBookedAppointmentsToAttach);
            }
            bookedAppointmentsCollectionNew = attachedBookedAppointmentsCollectionNew;
            appointments.setBookedAppointmentsCollection(bookedAppointmentsCollectionNew);
            appointments = em.merge(appointments);
            for (BookedAppointments bookedAppointmentsCollectionNewBookedAppointments : bookedAppointmentsCollectionNew) {
                if (!bookedAppointmentsCollectionOld.contains(bookedAppointmentsCollectionNewBookedAppointments)) {
                    Appointments oldAppointmentIdOfBookedAppointmentsCollectionNewBookedAppointments = bookedAppointmentsCollectionNewBookedAppointments.getAppointmentId();
                    bookedAppointmentsCollectionNewBookedAppointments.setAppointmentId(appointments);
                    bookedAppointmentsCollectionNewBookedAppointments = em.merge(bookedAppointmentsCollectionNewBookedAppointments);
                    if (oldAppointmentIdOfBookedAppointmentsCollectionNewBookedAppointments != null && !oldAppointmentIdOfBookedAppointmentsCollectionNewBookedAppointments.equals(appointments)) {
                        oldAppointmentIdOfBookedAppointmentsCollectionNewBookedAppointments.getBookedAppointmentsCollection().remove(bookedAppointmentsCollectionNewBookedAppointments);
                        oldAppointmentIdOfBookedAppointmentsCollectionNewBookedAppointments = em.merge(oldAppointmentIdOfBookedAppointmentsCollectionNewBookedAppointments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = appointments.getId();
                if (findAppointments(id) == null) {
                    throw new NonexistentEntityException("The appointments with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Appointments appointments;
            try {
                appointments = em.getReference(Appointments.class, id);
                appointments.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The appointments with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BookedAppointments> bookedAppointmentsCollectionOrphanCheck = appointments.getBookedAppointmentsCollection();
            for (BookedAppointments bookedAppointmentsCollectionOrphanCheckBookedAppointments : bookedAppointmentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Appointments (" + appointments + ") cannot be destroyed since the BookedAppointments " + bookedAppointmentsCollectionOrphanCheckBookedAppointments + " in its bookedAppointmentsCollection field has a non-nullable appointmentId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(appointments);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Appointments> findAppointmentsEntities() {
        return findAppointmentsEntities(true, -1, -1);
    }

    public List<Appointments> findAppointmentsEntities(int maxResults, int firstResult) {
        return findAppointmentsEntities(false, maxResults, firstResult);
    }

    private List<Appointments> findAppointmentsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Appointments.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Appointments findAppointments(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Appointments.class, id);
        } finally {
            em.close();
        }
    }

    public int getAppointmentsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Appointments> rt = cq.from(Appointments.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
