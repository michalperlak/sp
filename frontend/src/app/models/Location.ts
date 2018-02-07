export class Location {
  constructor(public longitude: number,
              public latitude: number) {
  }

  distance(location: Location): number {
    let p = 0.017453292519943295;    // Math.PI / 180
    let c = Math.cos;
    let a = 0.5 - c((location.latitude - this.latitude) * p) / 2
      + c(this.latitude * p) * c(location.latitude * p) * (1 - c((location.longitude - this.longitude) * p)) / 2;

    return 12742 * Math.asin(Math.sqrt(a)); // 2 * R; R = 6371 km
  }
}
