export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
  description: string;
}

export enum orderType {
  PLACED = 'PLACED',
  CONFIRMED = 'CONFIRMED',
  DELETED = 'DELETED',
  CANCELLED = 'CANCELLED',
}

export interface OrderApi {
  items: any[];
  total_count: number;
}

export interface OrderModel {
  orderID: number;
  status: orderType;
}
