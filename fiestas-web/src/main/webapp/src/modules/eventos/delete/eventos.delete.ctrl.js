(function (ng) {
    var mod = ng.module("eventoModule");
    mod.constant("eventosContext", "api/eventos");
    mod.controller('eventoDeleteCtrl', ['$scope', '$http', 'sportsContext', '$state',
        function ($scope, $http, eventosContext, $state) {
            var idSport = $state.params.sportId;
            /**
             * @ngdoc function
             * @name deleteEvento
             * @methodOf eventos.controller:eventoDeleteCtrl
             * @description
             * Esta función utiliza el protocolo HTTP para eliminar el evento.
             * @param {String} id El ID del evento a eliminar.
             */
            //TODO
        }
    ]);
}
)(window.angular);