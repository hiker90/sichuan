const Mock = require('mockjs')

const alerts = Mock.mock({
  'items|5': [{
    unit: '@sentence(1, 2)',
    time: '@datetime',
    info: '@sentence(5, 10)'
  }]
})

const handleResults = Mock.mock({
  'items|15': [{
    unit: '@sentence(1, 2)',
    time: '@datetime',
    info: '@sentence(5, 10)',
    result: '@integer(1, 2)'
  }]
})

const catalogs = Mock.mock({
  'items|30': [{
    name: '@sentence(1, 5)',
    unit: '@sentence(5, 10)',
    timestamp: '@datetime',
    creater: 'name'
  }]
})

const statisticts = Mock.mock({
  'items|30': [{
    totalNum: '@integer(1000, 20000)',
    unit: '@sentence(5, 10)',
    timestamp: '@datetime',
    updatedNum: '@integer(10, 200)'
  }]
})

const queryResults = Mock.mock({
  'items|30': [{
    name: '@sentence(5, 10)',
    keywords: ['CN', 'US', 'JP', 'EU', 'FR'],
    desc: '@sentence(10, 20)',
    time: '@datetime',
    unit: 'name'
  }]
})

const logs = Mock.mock({
  'items|30': [{
    operation: '@sentence(10, 20)',
    operater: 'name',
    timestamp: '@datetime',
    operateUnit: '@sentence(1, 2)'
  }]
})

module.exports = [
  {
    url: '/getKpis',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: {
          dataAmount: 102400,
          dataInterface: 9280,
          dataTable: 13600
        }
      }
    }
  },
  {
    url: '/getAlertInfo',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: alerts
      }
    }
  },
  {
    url: '/getHandleResult',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: handleResults
      }
    }
  },
  {
    url: '/queryData',
    type: 'post',
    response: config => {
      return {
        code: 200,
        success: true,
        data: queryResults
      }
    }
  },
  {
    url: '/catalog',
    type: 'post',
    response: config => {
      const { name, unit } = config.query

      if (name || unit) {
        return {
          code: 200,
          data: {
            items: [
              {
                name: '@sentence(1, 5)',
                unit: '@sentence(5, 10)',
                timestamp: '@datetime',
                creater: 'name'
              }
            ]
          }
        }
      } else {
        return {
          code: 200,
          data: catalogs
        }
      }
    }
  },
  {
    url: '/statistics',
    type: 'post',
    response: config => {
      const { start, end } = config.query

      if (start || end) {
        return {
          code: 200,
          success: true,
          data: {
            items: [
              {
                unit: '筛选结果',
                operater: '筛选结果',
                totalNum: 1,
                updatedNum: 1
              }
            ]
          }
        }
      } else {
        return {
          code: 200,
          success: true,
          data: statisticts
        }
      }
    }
  },
  {
    url: '/operateLogs',
    type: 'get',
    response: config => {
      return {
        code: 200,
        data: logs
      }
    }
  }
]
