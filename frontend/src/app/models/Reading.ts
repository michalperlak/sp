import {Location} from "./Location";

export class Reading {
  constructor(public deviceId: string,
              public gyroscope: Array<number>,
              public accelerometer: Array<number>,
              public location: Location,
              public timestamp: number) {
  }
}
