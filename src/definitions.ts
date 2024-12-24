export interface EasyAdsPlugin {
  init(config: Config): Promise<Result>;
  load(type: string, tag: string): Promise<Result>;
  destroy(callId: string): Promise<Result>;
}

export interface Config {
  rules: Array<{ tag: string, sort: Array<number>, percent: number }>, 
  apps: Array<{ tag: string, appId: string, index: number }>, 
  adspots: Array<{ tag: string, targets: Array<string>, options: Object }>,
}

export interface Result {
  code: string,
  data: Object,
  error?: Object
}
