export enum AnimalType {
  COW = 'COW',
  BUFFELOW = 'BUFFELOW',
}

export enum TestModeType {
  MANUAL = 'MANUAL',
  AUTOMATIC = 'AUTOMATIC',
}

export enum CollectionStatusType {
  ACCEPTED = 'ACCEPTED',
  REJECTED = 'REJECTED',
}

export const animalTypes = ['COW', 'BUFFELOW'];

export interface CollectionModel {
  id: number;
  type: AnimalType;
  testMode: TestModeType;
  status: CollectionStatusType;
  fat: number;
  snf: number;
  qwt: number;
  rate: number;
  amount: number;
  density: number;
  actose: number;
  salts: number;
  protein: number;
  temp: number;
  water: number;
  createdOn: any;
}

export interface AllCollectionApi {
  count: number;
  items: CollectionModel[];
}
