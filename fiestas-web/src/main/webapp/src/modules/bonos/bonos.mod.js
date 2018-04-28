(function (ng) {

    var mod = ng.module("bonosModule", ['ui.router']);

    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {

            var basePath = 'src/modules/bonos/';

            $urlRouterProvider.otherwise("/bonosList");

            $stateProvider.state('bonos', {
                url: '/bonos',
                abstract: true,
                views: {
                    'mainView': {
                        templateUrl: basePath + 'bonos.html',
                        controller: 'bonosCtrl',
                        controllerAs: 'ctrl'
                    }
                }
            }).state('bonosList', {
                url: '/list',
                parent: 'bonos',
                views: {
                    'listView': {
                        templateUrl: basePath + 'bonos.list.html'
                    }
                }
            }).state('bonosDetail', {
                url: '/{bonosId:int}/detail',
                parent: 'bonos',
                param: {bonosId: null},
                views: {
                    'listView': {
                        templateUrl: basePath + 'bonos.list.html'
                    },
                    'detailView': {
                        templateUrl: basePath + 'bonos.detail.html',
                        controller: 'bonosDetailCtrl',
                        controllerAs: 'ctrl'
                    }
                }

            }).state("bonosUpdate",{
              url: '/{bonosId:int}/update',
                parent: 'bonos',
                param: {bonosId: null},
                views: {
                    'detailView': {
                        templateUrl: basePath + '/create/bonos.new.html',
                        controller: 'bonosUpdateCtrl',
                        controllerAs: 'ctrl'
                    }
                }  
            });
        }
    ]);
})(window.angular);
