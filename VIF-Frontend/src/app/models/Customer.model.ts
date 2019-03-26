import { User } from './User.model';

export class Customer {
    id: number;
    username: string;
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

    public constructor() {
        this.activeFlg = 1;
    }
}