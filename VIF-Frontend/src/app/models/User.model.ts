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
    identityAvatar: string[];
    public constructor() {
        this.isOnline = 0;
        this.isDeleted = 0;
        this.activeFlg = -1;
        this.role = '-1';
        this.customer = new Customer();
    }
}