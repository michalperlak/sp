export class Location {
  constructor(public longitude: number,
              public latitude: number) {
  }

  static distance(location1: Location, location2: Location): number {
    let p = 0.017453292519943295;    // Math.PI / 180
    let c = Math.cos;
    let a = 0.5 - c((location1.latitude - location2.latitude) * p) / 2
      + c(location2.latitude * p) * c(location1.latitude * p) * (1 - c((location1.longitude - location2.longitude) * p)) / 2;

    return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
  }
}
