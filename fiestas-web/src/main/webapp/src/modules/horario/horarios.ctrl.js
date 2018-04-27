(function (ng) {
    var mod = ng.module("horarioModule");
    mod.constant("horarioContext", "horarios");
    mod.constant("contratosContext", "api/contratos");
    mod.controller('horarioCtrl', ['$scope', '$http','contratosContext','$state','horarioContext',
                /**
         * @ngdoc controller
         * @name horarios.controller:horariosCtrl
         * @description
         * Definición del controlador de Angular del módulo Horarios. 
         * Se crea el controlador con el cual se maneja el módulo.
         * En el controlador se definen los atributos y métodos que pueden
         * ser accedidos desde el HTML utilizando el $scope.
         * @param {Object} $scope Referencia injectada al Scope definida para este
         * controlador, el scope es el objeto que contiene las variables o 
         * funciones que se definen en este controlador y que son utilizadas 
         * desde el HTML.
         * @param {Object} $http Objeto injectado para la manejar consultas HTTP
         * @param {Object} contratosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de contratos en el Backend.
         * @param {Object} $state Dependencia injectada en la que se recibe el 
         * estado actual de la navegación definida en el módulo.
         * @param {Object} horariosContext Constante injectada que contiene la ruta
         * donde se encuentra el API de Horarios en el Backend.
         */
        function ($scope, $http,contratosContext,$state, horarioContext) {
            $http.get(contratosContext+'/'+$state.params.contratoId+'/'+horarioContext).then(function (response) {
                $scope.horariosRecords = response.data;
            });
        }
    ]);
}
)(window.angular);