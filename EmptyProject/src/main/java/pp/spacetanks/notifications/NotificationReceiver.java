package pp.spacetanks.notifications;

/**
 * Interface for any subscriber interested in notifications of events occurring in the game model.
 *
 * @see Notification
 */
public interface NotificationReceiver {
    void notify(Notification notification);
}
