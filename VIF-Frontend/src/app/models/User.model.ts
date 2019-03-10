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
    address: string;
    birthday: Date;

    public constructor() {
        this.isOnline = 0;
        this.isDeleted = 0;
        this.role = '-1';
        this.activeFlg = -1;
    }
}