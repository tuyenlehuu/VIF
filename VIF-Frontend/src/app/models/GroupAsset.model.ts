import { Asset } from './Asset.model';

export class GroupAsset {
    id: number;
    activeFlg: number;
    groupCode: string;
    groupName: string;
    typeOfAsset: number;

    public constructor() {
        this.activeFlg = 1;
        this.typeOfAsset = 1;
    }
}