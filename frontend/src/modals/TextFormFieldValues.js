class OfferFormField {
  contentText;
  type;

  constructor(contentText, type) {
    this.contentText = contentText;
    this.type = type;
  }
}

// Not sure for the names
export const OFFER_FORM_VALUES = [
  new OfferFormField("Date limite pour appliquer", "date"),
  new OfferFormField("Date de début", "date"),
  new OfferFormField("Date de fin", "date"),
  new OfferFormField("Courriel du Moniteur", "email"),
  new OfferFormField("Nom de la compagnie", "text"),
  new OfferFormField("Taux horaire minimum", "number"),
  new OfferFormField("Taux horaire maximum", "number"),
  new OfferFormField("Description du poste", "text"),
];
