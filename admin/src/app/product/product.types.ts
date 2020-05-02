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

export interface ProductModel {
  id: number;
  name: string;
  qwt: number;
  available: boolean;
  price: number;
  subCategoryName: string;
  subCategory: any;
}

export interface AllProductApi {
  code: number;
  items: ProductModel[];
}

export const ItemDetailTypeByCategory = {
  milk: ['FAT', 'WATER', 'SNF'],
};
