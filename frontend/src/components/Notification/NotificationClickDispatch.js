import handlers from "./NotificationClickHandlers";

const dispatchNotificationClickEvent = ({
  notificationType,
  data,
  dialogDispatch,
  onSelectionChanged,
}) => {
  if (notificationType === undefined || notificationType === null) return;

  if (notificationType in handlers) {
    handlers[notificationType].handle({
      data,
      dialogDispatch,
      onSelectionChanged,
    });
  } else {
    console.error(`Unhandled notification type: ${notificationType}`);
  }
};

export default dispatchNotificationClickEvent;
