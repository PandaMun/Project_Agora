const PARTICIPANT_MAIN_CLASS = 'participant main';
const PARTICIPANT_CLASS = 'participant';

function Participant(userName, position, isScreen) {
    this.userName = userName;
    this.position = position;
    let container = document.createElement('div');
    // container.className = isPresentMainParticipant() ? PARTICIPANT_CLASS : PARTICIPANT_MAIN_CLASS;
    container.className = PARTICIPANT_CLASS;
    container.id = userName;

    let span = document.createElement('span');
    let video = document.createElement('video');
    video.id = 'video-' + userName;
    video.autoplay = true;
    video.controls = false;

    console.log(this)

    let rtcPeer;
    // container.onclick = switchContainerClass;

    console.log(this.position)
    if (this.position === '반대') {
        document.getElementById('participants-opp').appendChild(container);
    } else if (this.position === '찬성') {
        document.getElementById('participants-agree').appendChild(container);
    } else if (this.position === '사회자') {
        document.getElementById('moderator').appendChild(container);
    }

    container.appendChild(video);


    if (isScreen) {
        let alternated = document.createElement('div');
        alternated.innerText = '화면 공유를 진행 중입니다.'
        alternated.style.width = '300';
        alternated.style.height = '225';
        alternated.style.color = 'white';
        alternated.style.display = 'flex';
        alternated.style.justifyContent = 'center';
        alternated.style.alignItems = 'center';

        container.appendChild(alternated)
        document.getElementById('screen').appendChild(video);

    }

    container.appendChild(span);

    span.appendChild(document.createTextNode(userName));

    this.getElement = function () {
        return container;
    }

    this.getVideoElement = function () {
        return video;
    }

    function switchContainerClass() {
        if (container.className === PARTICIPANT_CLASS) {
            var elements = Array.prototype.slice.call(document.getElementsByClassName(PARTICIPANT_MAIN_CLASS));
            elements.forEach(function (item) {
                item.className = PARTICIPANT_CLASS;
            });
            container.className = PARTICIPANT_MAIN_CLASS;
        } else {
            container.className = PARTICIPANT_CLASS;
        }
    }

    function isPresentMainParticipant() {
        return ((document.getElementsByClassName(PARTICIPANT_MAIN_CLASS)).length != 0);
    }

    this.offerToReceiveVideo = function (error, offerSdp, wp) {
        if (error) return console.error("sdp offer error")
        console.log('Invoking SDP offer callback function');
        console.log(userName)
        // let msg = {
        //     id: "receiveVideoFrom",
        //     sender: userName,
        //     sdpOffer: offerSdp
        // };
        // sendMessage(msg);
    }


    this.onIceCandidate = function (candidate, wp) {
        console.log("Local candidate" + JSON.stringify(candidate));
        // var message = {
        //     id: 'onIceCandidate',
        //     candidate: candidate,
        //     userName: userName
        // };
        // sendMessage(message);
    }

    Object.defineProperty(this, 'rtcPeer', {writable: true});

    this.dispose = function () {
        console.log('Disposing participant ' + this.userName);
        this.rtcPeer.dispose();
        container.parentNode.removeChild(container);
    };
}

export default Participant;