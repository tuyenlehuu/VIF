import { User } from './User.model';

export class Customer {
    id: number;
    code: string;
    email: string;
    activeFlg: number;
    fullName: string;
    avatar: string;
    orginalCCQPrice: number;
    totalCcq: number;
    identityNumber: string;
    dateOfBirth: Date;
    signContractDate: Date;
    identityDocFront: string;
    identityDocBack: string;
    users: User[];

    public constructor() {
        this.activeFlg = 1;
        this.users = [];
    }
}