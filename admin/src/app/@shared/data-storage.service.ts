import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private store: any;

  public constructor() {
    this.store = {};
  }

  setData = (data: any) => {
    const keys = Object.keys(data);
    keys.forEach((key) => {
      this.store[key] = data[key];
    });
  };

  getData = (key: string) => {
    return this.store[key];
  };
}
