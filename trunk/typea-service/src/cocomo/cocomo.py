# -*- coding: utf-8 -*-

import math

class Cocomo(object):
    PHASE_COL_SIZE = 9

    def __init__(self, calc_mode='kdsi', kdsi=0, effort=0):
        self.calc_mode         = calc_mode
        self.kdsi              = kdsi       # プログラムサイズ
        self.effort            = effort     # 工数
        
        self.tdev              = None       # 開発期間
        self.fsp               = None       # 開発要員
        self.prod              = None       # 生産性
        self.plan_requirements = None       # 計画と要件定義
        self.product_design    = None       # 製品設計
        self.detailed_design   = None       # 詳細設計
        self.code_unit_test    = None       # プログラミングと単体テスト
        self.integration_test  = None       # 統合とテスト
        self.total             = None       # 合計

        self.phase_plan_requirements = []
        self.phase_product_design    = []
        self.phase_programming       = []
        self.phase_integration_test  = []
        self.phase_total             = []

        self.calc()

    def details(self):
        result = []
        for i in range(self.PHASE_COL_SIZE):
            result.append((self.phase_plan_requirements[i],
                           self.phase_product_design[i],
                           self.phase_programming[i],
                           self.phase_integration_test[i],
                           self.phase_total[i],
                        ))
        return result
    
    def calc(self):
        if self.calc_mode == 'kdsi':
            self.effort = 2.4 * math.exp(1.05 * math.log(float(self.kdsi)))
        elif self.calc_mode == 'effort':
            self.kdsi = math.exp(math.log(float(self.effort) / 2.4) / 1.05)
        else:
            raise KeyError(r"not appliacble parameter '%s'" % self.calc_mode)

        phase_plan_requirements_ratio = (0.46, 0.20, 0.03, 0.03, 0.06, 0.15, 0.02, 0.05) 
        phase_product_design_ratio    = (0.15, 0.40, 0.14, 0.05, 0.06, 0.11, 0.02, 0.07)
        phase_programming_ratio       = (0.05, 0.10, 0.58, 0.04, 0.06, 0.06, 0.06, 0.05)
        phase_integration_test_ratio  = (0.03, 0.06, 0.34, 0.02, 0.34, 0.07, 0.07, 0.07)

        self.tdev              = 2.5 * math.exp(0.38 * math.log(self.effort))
        self.fsp               = self.effort / self.tdev
        self.prod              = self.kdsi / self.effort
        self.plan_requirements = self.effort * (6.0 / 100.0)
        self.product_design    = self.effort * (16.0 / 100.0)
        self.detailed_design   = self.effort * ((-0.7213 * math.log(self.kdsi) + 26.5) / 100)
        self.code_unit_test    = self.effort * ((-1.4427 * math.log(self.kdsi) + 43.0) / 100)
        self.integration_test  = self.effort - (self.product_design + self.detailed_design + self.code_unit_test)
        self.total = sum( ( self.plan_requirements,
                            self.product_design,
                            self.detailed_design,
                            self.code_unit_test,
                            self.integration_test ))

        programming_ratio = (self.detailed_design + self.code_unit_test)
        self.phase_plan_requirements = self.list_summary([self.plan_requirements  * x for x in phase_plan_requirements_ratio])
        self.phase_product_design    = self.list_summary([self.product_design     * x for x in phase_product_design_ratio])
        self.phase_programming       = self.list_summary([programming_ratio       * x for x in phase_programming_ratio])
        self.phase_integration_test  = self.list_summary([self.integration_test   * x for x in phase_integration_test_ratio])

        for i in range(self.PHASE_COL_SIZE):
            self.phase_total.append( sum ((self.phase_plan_requirements[i], 
                                           self.phase_product_design[i],
                                           self.phase_programming[i],
                                           self.phase_integration_test[i])
                                     ))
        #self.phase_total.append(sum(self.phase_total[:self.PHASE_COL_SIZE-2])) 

    def list_summary(self, lst):
        ttl = sum(lst)
        lst.append(ttl)
        return lst

    def to_dict(self):
        return {
                'calc_mode'                 :self.calc_mode,
                'kdsi'                      :self.kdsi,
                'effort'                    :self.effort,
                'tdev'                      :self.tdev,
                'fsp'                       :self.fsp,
                'prod'                      :self.prod,
                'plan_requirements'         :self.plan_requirements,
                'product_design'            :self.product_design,
                'detailed_design'           :self.detailed_design,
                'code_unit_test'            :self.code_unit_test,
                'integration_test'          :self.integration_test,
                'total'                     :self.total,
                'phase_plan_requirements'   :self.phase_plan_requirements,
                'phase_product_design'      :self.phase_product_design,
                'phase_programming'         :self.phase_programming,
                'phase_integration_test'    :self.phase_integration_test,
                'phase_total'               :self.phase_total,
                }
