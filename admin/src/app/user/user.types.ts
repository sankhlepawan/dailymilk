export interface UserApi {
  items: UserModel[];
  count: number;
}

export interface RoleApi {
  items: RoleType[];
  count: number;
}

export interface AddressType {
  id: number;
  location: string;
  pincode: string;
  state: string;
  city: string;
  country: string;
  fullAddress: string;
}

export interface userPreferenceType {
  id: number;
  language: string;
  lastSelectedMenu: string;
}

export interface RoleType {
  id: number;
  roleName: string;
  description: string;
}

export interface UserModel {
  id: number;
  firstName: string;
  lastName: string;
  whatsappNumber: string;
  mobile: string;
  address: AddressType;
  userPreference: userPreferenceType;
  enable: boolean;
  deleted: boolean;
  roles: RoleType[];
}

export interface userFilter {
  whatsappNumber: string;
  name: string;
  role: string;
}

export const dbRoleTypes = {
  Supplier: 'ROLE_SUPPLIER',
  User: 'ROLE_USER',
};

export const UIRoleTypes = {
  ROLE_SUPPLIER: 'Supplier',
  ROLE_USER: 'User',
};
