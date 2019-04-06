
import { GroupAsset } from './GroupAsset.model';


export class Asset {
    id: number;
    branchCode: string;
    assetCode: string;
    assetName: string;
    amount: number;
    orginalPrice: number;
    currentPrice: number;
    description: string;
    activeFlg: number;

    groupAsset: GroupAsset;
   
    public constructor() {
        this.activeFlg = 1;
        this.currentPrice = 0;
        this.orginalPrice = 0;
        this.amount = 0;
        this.groupAsset = new GroupAsset();
    }
   
}