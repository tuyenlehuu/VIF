import { Customer } from './Customer.model';

export class User {
    id: number;
    username: string;
    password: string;
    email: string;
    activeFlg: number;
    role: string;
    isOnline: number;
    isDeleted: number;
    token: string;
    customerId: string;
    avatar: string;
    customer: Customer
    identityDocAvatar: string[];
}