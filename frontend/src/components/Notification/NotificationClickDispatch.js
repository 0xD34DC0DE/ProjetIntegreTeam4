import handlers from "./NotificationClickHandlers";

const dispatchNotificationClickEvent = ({
  notificationType,
  data,
  dialogDispatch,
  selectionDispatch,
}) => {
  if (notificationType === undefined || notificationType === null) return;
  if (notificationType in handlers) {
    handlers[notificationType].handle({
      data,
      dialogDispatch,
      selectionDispatch,
    });
  } else {
    console.error(`Unhandled notification type: ${notificationType}`);
  }
};

export default dispatchNotificationClickEvent;
