export class AppParam {
  id: number;
  description: string;
  propKey: string;
  propType: string;
  propValue: string;
  activeFlg: number;

  public constructor() {
    this.activeFlg = -1;
  }
}