export class Reading {
  constructor(public deviceId: string,
              public gyroscopeData: Array<number>,
              public accelerometerData: Array<number>,
              public location: Location) {
  }
}
