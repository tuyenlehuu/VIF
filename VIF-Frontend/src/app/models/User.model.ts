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

    public constructor() {
        this.isOnline = 0;
        this.isDeleted = 0;
        this.activeFlg = -1;
        this.role = '-1'
    }
}