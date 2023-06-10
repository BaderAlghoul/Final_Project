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
public class UserJpaController implements Serializable {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) {
        if (user.getBookedAppointmentsCollection() == null) {
            user.setBookedAppointmentsCollection(new ArrayList<BookedAppointments>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<BookedAppointments> attachedBookedAppointmentsCollection = new ArrayList<BookedAppointments>();
            for (BookedAppointments bookedAppointmentsCollectionBookedAppointmentsToAttach : user.getBookedAppointmentsCollection()) {
                bookedAppointmentsCollectionBookedAppointmentsToAttach = em.getReference(bookedAppointmentsCollectionBookedAppointmentsToAttach.getClass(), bookedAppointmentsCollectionBookedAppointmentsToAttach.getId());
                attachedBookedAppointmentsCollection.add(bookedAppointmentsCollectionBookedAppointmentsToAttach);
            }
            user.setBookedAppointmentsCollection(attachedBookedAppointmentsCollection);
            em.persist(user);
            for (BookedAppointments bookedAppointmentsCollectionBookedAppointments : user.getBookedAppointmentsCollection()) {
                User oldUserIdOfBookedAppointmentsCollectionBookedAppointments = bookedAppointmentsCollectionBookedAppointments.getUserId();
                bookedAppointmentsCollectionBookedAppointments.setUserId(user);
                bookedAppointmentsCollectionBookedAppointments = em.merge(bookedAppointmentsCollectionBookedAppointments);
                if (oldUserIdOfBookedAppointmentsCollectionBookedAppointments != null) {
                    oldUserIdOfBookedAppointmentsCollectionBookedAppointments.getBookedAppointmentsCollection().remove(bookedAppointmentsCollectionBookedAppointments);
                    oldUserIdOfBookedAppointmentsCollectionBookedAppointments = em.merge(oldUserIdOfBookedAppointmentsCollectionBookedAppointments);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getId());
            Collection<BookedAppointments> bookedAppointmentsCollectionOld = persistentUser.getBookedAppointmentsCollection();
            Collection<BookedAppointments> bookedAppointmentsCollectionNew = user.getBookedAppointmentsCollection();
            List<String> illegalOrphanMessages = null;
            for (BookedAppointments bookedAppointmentsCollectionOldBookedAppointments : bookedAppointmentsCollectionOld) {
                if (!bookedAppointmentsCollectionNew.contains(bookedAppointmentsCollectionOldBookedAppointments)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BookedAppointments " + bookedAppointmentsCollectionOldBookedAppointments + " since its userId field is not nullable.");
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
            user.setBookedAppointmentsCollection(bookedAppointmentsCollectionNew);
            user = em.merge(user);
            for (BookedAppointments bookedAppointmentsCollectionNewBookedAppointments : bookedAppointmentsCollectionNew) {
                if (!bookedAppointmentsCollectionOld.contains(bookedAppointmentsCollectionNewBookedAppointments)) {
                    User oldUserIdOfBookedAppointmentsCollectionNewBookedAppointments = bookedAppointmentsCollectionNewBookedAppointments.getUserId();
                    bookedAppointmentsCollectionNewBookedAppointments.setUserId(user);
                    bookedAppointmentsCollectionNewBookedAppointments = em.merge(bookedAppointmentsCollectionNewBookedAppointments);
                    if (oldUserIdOfBookedAppointmentsCollectionNewBookedAppointments != null && !oldUserIdOfBookedAppointmentsCollectionNewBookedAppointments.equals(user)) {
                        oldUserIdOfBookedAppointmentsCollectionNewBookedAppointments.getBookedAppointmentsCollection().remove(bookedAppointmentsCollectionNewBookedAppointments);
                        oldUserIdOfBookedAppointmentsCollectionNewBookedAppointments = em.merge(oldUserIdOfBookedAppointmentsCollectionNewBookedAppointments);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = user.getId();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<BookedAppointments> bookedAppointmentsCollectionOrphanCheck = user.getBookedAppointmentsCollection();
            for (BookedAppointments bookedAppointmentsCollectionOrphanCheckBookedAppointments : bookedAppointmentsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the BookedAppointments " + bookedAppointmentsCollectionOrphanCheckBookedAppointments + " in its bookedAppointmentsCollection field has a non-nullable userId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
